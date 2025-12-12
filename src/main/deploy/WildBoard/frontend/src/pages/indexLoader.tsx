import { h, render } from "preact";
import Page from "./index.tsx";
import { SocketStoreProvider } from "../socket/SocketProvider.tsx";

render(
  <SocketStoreProvider>
    <Page />
  </SocketStoreProvider>,
  document.getElementsByTagName("main")[0]!
);
