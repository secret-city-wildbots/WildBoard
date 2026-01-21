import { h } from "/home/lvuser/deploy/WildBoard/frontend/src/node_modules/preact";
import TabbedContainer from "/home/lvuser/deploy/WildBoard/frontend/src/components/TabbedContainer.tsx";
import Container from "/home/lvuser/deploy/WildBoard/frontend/src/components/Container.tsx";
import { WsEventBus } from "/home/lvuser/deploy/WildBoard/frontend/src/ws/WSEventBus.ts";
[IMPORTS]

export default function () {
    const socket = new WsEventBus(`ws://${window.location.hostname}:5805`);

    const tabs = [
        [TABS]
    ];

    return (
        <Container>
            <div class="row" style="padding-left: 1rem;">
                <div class="col column">
                    <TabbedContainer tabs={tabs} />
                </div>
                <div class="col column" style="flex: 0 0 12rem;">
                    [SIDEPANELS]
                </div>
            </div>
        </Container>
    );
}
