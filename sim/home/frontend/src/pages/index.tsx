import { h } from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/Wildboard/frontend/src/node_modules/preact";
import TabbedContainer from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/Wildboard/frontend/src/components/TabbedContainer.tsx";
import Container from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/Wildboard/frontend/src/components/Container.tsx";
import { WsEventBus } from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/Wildboard/frontend/src/ws/WSEventBus.ts";
import Checklist from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/Checklist.tsx";



import Placeholder from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/Placeholder.tsx";
import Placeholder from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/Placeholder.tsx";

import Placeholder from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/Placeholder.tsx";
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

import LooptimeMonitor from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/LooptimeMonitor.tsx";

import LooptimeMonitor from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/LooptimeMonitor.tsx";


import LooptimeMonitor from "C:/Users/jaspe/coding/WildBoard/src/main/deploy/WildBoard/frontend/src/panels/LooptimeMonitor.tsx";


export default function () {
    const socket = new WsEventBus(`ws://${window.location.hostname}:5805`);

    const tabs = [
        {title: "Checklist",content: (<Container><div class="row"><Checklist ></Checklist></div></Container>)},{title: "Setup/Auto",content: (<Container><div class="row"></div></Container>)},{title: "TeleOp",content: (<Container><div class="row"></div></Container>)},{title: "Subsystems",content: (<Container><div class="row"><div class="col-5 column"><div class="column-item bubble"><Placeholder text={"SWERVES"} height={30} ></Placeholder></div><div class="column-item bubble"><Placeholder text={"teST"} height={10} ></Placeholder></div></div><div class="col-3 column"><div class="column-item bubble"><Placeholder text={"Climb"} height={20} ></Placeholder></div><div class="column-item bubble"><Placeholder text={"BLAH"} height={10} ></Placeholder></div></div><div class="col-4 column"><DashboardSubRow><div class="bubble"><SimpleSubsystem socket={socket} name={"Shooter"} id={1} velocity ></SimpleSubsystem></div><div class="bubble"><SimpleSubsystem socket={socket} name={"Intake"} id={2} velocity ></SimpleSubsystem></div></DashboardSubRow><DashboardSubRow><div class="bubble"><SimpleSubsystem socket={socket} name={"Indexer"} id={3} velocity ></SimpleSubsystem></div><div class="bubble"><SimpleSubsystem socket={socket} name={"Funnel"} id={4} velocity ></SimpleSubsystem></div></DashboardSubRow><DashboardSubRow><div class="bubble"><SimpleSubsystem socket={socket} name={"Turret"} id={5} ></SimpleSubsystem></div><div class="bubble"><SimpleSubsystem socket={socket} name={"Turret Hood"} id={6} absolute ></SimpleSubsystem></div></DashboardSubRow></div></div></Container>)},{title: "TESt",content: (<Container><div class="row"><div class="col-3 column"><div class="column-item bubble"><LooptimeMonitor socket={socket} id={7} ></LooptimeMonitor></div></div><div class="col-6 column"><div class="column-item bubble"><LooptimeMonitor socket={socket} id={8} ></LooptimeMonitor></div></div><div class="col-3 column"><div class="column-item bubble"><LooptimeMonitor socket={socket} id={9} ></LooptimeMonitor></div></div></div></Container>)},
    ];

    return (
        <Container>
            <div class="row" style="padding-left: 0rem;">
                <div class="col column" style="padding: 0;padding-left: 1rem;">
                    <TabbedContainer tabs={tabs} />
                </div>
                <div class="col column sidepanel" style="flex: 0 0 14rem;">
                    <div class="column-item" style="padding-bottom: 0;">
<LooptimeMonitor socket={socket} id={0} ></LooptimeMonitor>
</div>
                </div>
            </div>
        </Container>
    );
}
