import { h } from "preact";

import DashboardItem from "../components/DashboardItem.tsx";

export default function(text: String, height:number = 5) {

    return (
        <DashboardItem>
            <div style={`width: 100%; text-align: center; height:${height}rem; font-size: 2rem;`}>
                {text}
            </div>
        </DashboardItem>
    );
};