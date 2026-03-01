import { h, Fragment } from "[DEPLOY]/Wildboard/frontend/src/node_modules/preact";
import { useMemo } from "[DEPLOY]/Wildboard/frontend/src/node_modules/preact/hooks";
import TabbedContainer from "[DEPLOY]/Wildboard/frontend/src/components/TabbedContainer.tsx";
import Container from "[DEPLOY]/Wildboard/frontend/src/components/Container.tsx";
import { WsEventBus } from "[DEPLOY]/Wildboard/frontend/src/ws/WSEventBus.ts";
[IMPORTS]

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
        [TABS]
    ], []);

    const sidepanels = useMemo(() => (
        <>
            [SIDEPANELS]
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
