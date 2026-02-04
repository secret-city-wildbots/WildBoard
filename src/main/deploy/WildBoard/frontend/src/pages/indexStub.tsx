import { h } from "[DEPLOY]/Wildboard/frontend/src/node_modules/preact";
import TabbedContainer from "[DEPLOY]/Wildboard/frontend/src/components/TabbedContainer.tsx";
import Container from "[DEPLOY]/Wildboard/frontend/src/components/Container.tsx";
import { WsEventBus } from "[DEPLOY]/Wildboard/frontend/src/ws/WSEventBus.ts";
[IMPORTS]

export default function () {
    const socket = new WsEventBus(`ws://${window.location.hostname}:5805`);

    const tabs = [
        [TABS]
    ];

    return (
        <Container>
            <div class="row" style="padding-left: 0rem;">
                <div class="col column" style="padding: 0;padding-left: 1rem;">
                    <TabbedContainer tabs={tabs} />
                </div>
                <div class="col column sidepanel" style="flex: 0 0 14rem;">
                    [SIDEPANELS]
                </div>
            </div>
        </Container>
    );
}
