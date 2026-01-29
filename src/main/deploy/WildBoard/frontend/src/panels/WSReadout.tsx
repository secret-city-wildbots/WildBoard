import { h } from "preact";
import { useState } from "preact/hooks";
import { WsEventBus } from "../ws/WSEventBus";
import Readout from "../components/Readout";

interface Props {
  defaultText?: string;
  id: number;
  socket: WsEventBus;
  precision?: number;

  chars?: number;
  color?: string;
  small?: boolean;
  angle?: boolean;
  temperature?: boolean;
}

export default function ({ id, socket, defaultText = "_", chars, precision, color, small, angle = false, temperature = false, }: Props) {
  const [text, setText] = useState(defaultText);

  socket.subscribe(id, (data: string) => {
    //do nothing if blank
    if (data === undefined || data === null) return;

    //handle numbers
    let numData:number = Number(data);
    if (!Number.isNaN(Number(data))) {
      if (precision !== undefined) {
        if (precision == 0) {
          data = Math.floor(numData)+"";
        } else {
          data = numData.toFixed(precision);
        }
      }
    }

    //push val
    setText(data + "");
  });

  return (
    <Readout text={text} angle={angle} temperature={temperature} small={small} color={color} chars={chars}/>
  );
}