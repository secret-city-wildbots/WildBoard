import { h, Fragment } from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/Wildboard/frontend/src/node_modules/preact";
import { useMemo } from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/Wildboard/frontend/src/node_modules/preact/hooks";
import TabbedContainer from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/Wildboard/frontend/src/components/TabbedContainer.tsx";
import Container from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/Wildboard/frontend/src/components/Container.tsx";
import { WsEventBus } from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/Wildboard/frontend/src/ws/WSEventBus.ts";
import Checklist from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/Checklist.tsx";


import Placeholder from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/Placeholder.tsx";

import CameraFeed from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/CameraFeed.tsx";
import CameraFeed from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/CameraFeed.tsx";
import DashboardSubRow from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/DashboardSubRow.tsx";
import CameraFeed from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/CameraFeed.tsx";
import CameraFeed from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/CameraFeed.tsx";
import DashboardSubRow from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/DashboardSubRow.tsx";

import Placeholder from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/Placeholder.tsx";


import SwerveModules from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/SwerveModules.tsx";

import Placeholder from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/Placeholder.tsx";

import SimpleSubsystem from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/SimpleSubsystem.tsx";
import SimpleSubsystem from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/SimpleSubsystem.tsx";
import DashboardSubRow from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/DashboardSubRow.tsx";
import SimpleSubsystem from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/SimpleSubsystem.tsx";
import SimpleSubsystem from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/SimpleSubsystem.tsx";
import DashboardSubRow from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/DashboardSubRow.tsx";
import SimpleSubsystem from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/SimpleSubsystem.tsx";
import SimpleSubsystem from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/SimpleSubsystem.tsx";
import DashboardSubRow from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/DashboardSubRow.tsx";


import LooptimeMonitor from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/LooptimeMonitor.tsx";
import PingMonitor from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/PingMonitor.tsx";
import FPSMonitor from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/FPSMonitor.tsx";
import MasterAlarms from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/MasterAlarms.tsx";


export default function () {
    const socket = useMemo(
        () => new WsEventBus(`ws://${window.location.hostname}:5805`),
        []
    );

    // Memoize the tabs and sidepanels so their element trees aren't
    // regenerated on every render. Regenerating those trees can cause
    // Preact to remount child components when the parent updates at
    // high frequency (e.g. every 20ms packets).
    const tabs = useMemo(() => [
        {title: "Checklist",content: (<Container><div class="row"><Checklist ></Checklist></div></Container>)},{title: "Setup/Auto",content: (<Container><div class="row"></div></Container>)},{title: "TeleOp",content: (<Container><div class="row"><div class="col-2 column"><div class="column-item bubble"><Placeholder text={"MAP"} height={100} ></Placeholder></div></div><div class="col-6 column"><DashboardSubRow><div class="bubble"><CameraFeed port={5801} ></CameraFeed></div><div class="bubble"><CameraFeed port={5802} ></CameraFeed></div></DashboardSubRow><DashboardSubRow><div class="bubble"><CameraFeed port={5803} ></CameraFeed></div><div class="bubble"><CameraFeed port={5804} ></CameraFeed></div></DashboardSubRow></div><div class="col-4 column"><div class="column-item bubble"><Placeholder text={"OVERRIDES"} height={100} ></Placeholder></div></div></div></Container>)},{title: "Subsystems",content: (<Container><div class="row"><div class="col-4 column"><div class="column-item bubble"><SwerveModules socket={socket} id={4} ></SwerveModules></div></div><div class="col-3 column"><div class="column-item bubble"><Placeholder text={"Climb"} height={20} ></Placeholder></div></div><div class="col-5 column"><DashboardSubRow><div class="bubble"><SimpleSubsystem socket={socket} name={"Shooter"} id={5} velocity ></SimpleSubsystem></div><div class="bubble"><SimpleSubsystem socket={socket} name={"Intake"} id={6} velocity ></SimpleSubsystem></div></DashboardSubRow><DashboardSubRow><div class="bubble"><SimpleSubsystem socket={socket} name={"Indexer"} id={7} velocity ></SimpleSubsystem></div><div class="bubble"><SimpleSubsystem socket={socket} name={"Spindexer"} id={8} velocity ></SimpleSubsystem></div></DashboardSubRow><DashboardSubRow><div class="bubble"><SimpleSubsystem socket={socket} name={"Turret"} id={9} ></SimpleSubsystem></div><div class="bubble"><SimpleSubsystem socket={socket} name={"Turret Hood"} id={10} absolute ></SimpleSubsystem></div></DashboardSubRow></div></div></Container>)},
    ], []);

    const sidepanels = useMemo(() => (
        <>
            <div class="column-item" style="padding-bottom: 0;">
<LooptimeMonitor socket={socket} id={0} ></LooptimeMonitor>
</div><div class="column-item" style="padding-bottom: 0;">
<PingMonitor socket={socket} id={1} ></PingMonitor>
</div><div class="column-item" style="padding-bottom: 0;">
<FPSMonitor socket={socket} id={2} ></FPSMonitor>
</div><div class="column-item" style="padding-bottom: 0;">
<MasterAlarms socket={socket} cols={2} id={3} descriptions={["Swerve Overheat","Subsystem Overheat","Canbus Error","Short Detected","Joystick Disconnect","IMU Failure/Disconnect","Ping High/Failed","FPS Low","Loop Time too High","Battery Voltage Low"]} texts={["SWOH","SSOH","CNBS","SHRT","JOYS","IMU","PING","FPS","LOOP","BATT"]} ></MasterAlarms>
</div>
        </>
    ), []);

    return (
        <Container>
            <div class="row" style="padding-left: 0rem;">
                <div class="col column" style="padding: 0;padding-left: 1rem;">
                    <TabbedContainer tabs={tabs} />
                </div>
                <div class="col column sidepanel" style="flex: 0 0 12rem;">
                    {sidepanels}
                </div>
            </div>
        </Container>
    );
}
