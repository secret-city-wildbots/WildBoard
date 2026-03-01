import { h } from "preact";

export default function({ port }: { port: number }) {
    return (
        <image href={`10.42.65.2:${port}`} style="width: 100%; height: auto; object-fit: contain;" />
    );
};