import { h } from "preact";
import TabbedContainer from "../components/TabbedContainer.tsx";
import Container from "../components/Container.tsx";
import FlexRow from "../components/FlexRow.tsx";
import Checklist from "../legacypanels/Checklist.tsx";
import LooptimeMonitor from "../panels/LooptimeMonitor.tsx";
import { WsEventBus } from "../ws/WSEventBus.ts";

export default function () {
    const socket = new WsEventBus(`ws://${window.location.hostname}:5805`);

    const tabs = [
        {
            title: "Checklist",
            content: (<Container>
                <Checklist />
            </Container>),
        },
    ];

    return (
        <Container>
            <div class="row" style="padding-left: 1rem;">
                <div class="col column">
                    <TabbedContainer tabs={tabs} />
                </div>
                <div class="col column" style="flex: 0 0 12rem;">
                    <div class="column-item" style="padding-bottom: 0;">
                        <FlexRow>
                            <label class="label-small" style="margin-right: 0; padding-right: 0;">
                                Loop (ms):{" "}
                            </label>
                            <LooptimeMonitor id={0} socket={socket}/>
                        </FlexRow>
                    </div>
                </div>
            </div>
        </Container>
    );
}
