import { h } from "preact";
import { useEffect, useState } from "preact/hooks";
import { WsEventBus } from "../ws/WSEventBus";
import FlexRow from "../components/FlexRow";

interface Props {
  id: number;
  socket: WsEventBus;
}

export default function ({ id, socket }: Props) {
  const [text, setText] = useState("20");

  socket.subscribe(id, (data: string) => {
    setText(data);
  });

  return (
    <FlexRow>
      <label class="label-small" style="margin-right: 0; padding-right: 0;">
        Loop (ms): 
      </label>
      <div>{text}</div>
    </FlexRow>
  );
}
