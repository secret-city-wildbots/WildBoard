import { h, Fragment } from "preact";
import ArmableButton from "../components/ArmableButton.tsx";
import Switch from "../components/Switch.tsx";
import { WsEventBus } from "../ws/WSEventBus.ts";
import Readout from "../components/Readout.tsx";
import { useState } from "preact/hooks";

interface Props {
    absolute?: boolean;
    unit?: string;
    fillContainer?: boolean;
    velocity?: boolean;
    precision?: number;
    name: string;

    id: number;
    socket: WsEventBus;
}

export default function ({
    absolute = false,
    unit,
    fillContainer = false,
    velocity = false,
    precision = 0,
    name,
    id,
    socket,
}: Props) {
    const [pos, setPos] = useState("_");
    const [temp, setTemp] = useState("_");

    if (!unit) {
        unit = velocity ? "rpm" : "deg";
    }

    socket.subscribe(id, (data:string) => {
        const index = data.indexOf(",");
        if (index < 0) return;

        let pos = data.substring(0,index);
        let temp = data.substring(index+1);

        setPos(pos.length > 3 ? pos.substring(0,3):pos);
        setTemp(temp.length > 3 ? temp.substring(0,3):temp);

        console.info(data);
    });

    const onToggle = (on: boolean) => {
        socket.send(id, on ? "ul" : "l");
    };

    const onClick = () => {
        //start calibrating
        socket.send(id, "c1");
    };

    const onRelease = () => {
        //stop calibrating
        socket.send(id, "c0");
    };

    return (
        <div
            class={fillContainer ? " h-100":""}
            style={{ //vertically align in column
                display: "flex",
                justifyContent: "center",
                flexDirection: "column",
                padding: (fillContainer ? "0.5rem" : "1rem"),
            }}
        >
            <label>{name}</label>
            {!velocity && !absolute ? (
                <>
                    <ArmableButton
                        text="CALIB"
                        onToggle={onToggle}
                        onClick={onClick}
                        onRelease={onRelease}
                    />
                    {/*we need to detect chrome, as it likes to interpret the <br> as being huge, which looks bad.*/}
                    {!(typeof InstallTrigger !== 'undefined') ? (<></>) : (<br />)}
                </>
            ) : (
                <></>
            )}
            {absolute ? (
                <div style={{ //vertically align them in a row.
                    //TODO should prob split this into a class.
                    display: "flex",
                    justifyContent: "center",
                    flexDirection: "row",
                    alignItems: "center",
                }}>
                    <div>
                        <div style="min-height: 2.5rem; margin-top: 1rem;" class="flex-row">
                            <label class="label-small pr-3">
                                {velocity ? "Vel" : "Pos"} ({unit})
                            </label>
                            <Readout text={pos} chars={3} />
                        </div>
                        <div style="min-height: 2.5rem" class="flex-row">
                            <label class="label-small pr-3">Temp (C)</label>
                            <Readout text={temp} temperature chars={3} />
                        </div>
                    </div>
                    <div style="margin-left: 0.25rem;">
                        <Switch onColor="#ef0001" offColor="rgba(116, 255, 6, 1)" onToggle={onToggle} vertical />
                    </div>
                </div>
            ) : (
                <>
                    <div style="min-height: 2.5rem; margin-top: 0.5rem;" class="flex-row">
                        <label class="label-small pr-2">
                            {velocity ? "Vel" : "Pos"} ({unit})
                        </label>
                        <Readout text={pos} chars={3} />
                    </div>
                    <div style="min-height: 2.5rem" class="flex-row">
                        <label class="label-small pr-3">Temp (C)</label>
                        <Readout text={temp} temperature chars={3} />
                    </div>
                </>
            )}
        </div>
    );
};