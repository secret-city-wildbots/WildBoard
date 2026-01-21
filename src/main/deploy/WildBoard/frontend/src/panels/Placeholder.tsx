import { h } from "preact";

import DashboardItem from "../components/DashboardItem.tsx";

interface Props {
  text: String;
  height?: number;
}

export default function({text, height = 5}: Props) {

    return (
        <DashboardItem>
            <div style={`width: 100%; text-align: center; height:${height}rem; font-size: 2rem;`}>
                {text}
            </div>
        </DashboardItem>
    );
};