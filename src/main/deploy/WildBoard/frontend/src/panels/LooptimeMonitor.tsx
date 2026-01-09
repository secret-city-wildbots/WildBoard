import { h } from "preact";
import { useEffect, useState } from "preact/hooks";
import { WsEventBus } from "../ws/WSEventBus";

interface Props {
  id: number;
  socket: WsEventBus;
}

export default function({ id, socket }: Props) {
  const [text, setText] = useState("20");

  socket.subscribe(id, (data:string) => {
    setText(data);
  });

  return (
    <div>
      {text}
    </div>
  );
};