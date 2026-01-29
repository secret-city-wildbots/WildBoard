import { h, Fragment } from 'preact';
import NTReadout from "../components/NTReadout.tsx";
import ArmableButton from "../components/ArmableButton.tsx";
import Button from "../components/Button.tsx";
import { WsEventBus } from '../ws/WSEventBus.ts';
import { MutableRef, useEffect, useRef } from 'preact/hooks';

interface ReadoutProps {
  id: number;
  socket: WsEventBus;
}

const SwerveModules = ({
    id,
    socket,
}: ReadoutProps) => {
    const canvasRef = useRef(null);
    let swerves = [
        {a: 0, v: 1},
        {a: 0, v: 1},
        {a: 0, v: 1},
        {a: 0, v: 1},
    ]

    function render(canvasRef:MutableRef<null>) {
        const ctx: CanvasRenderingContext2D = canvasRef.current.getContext("2d");

        if (!ctx) return;

        ctx.strokeStyle = "black";
        ctx.lineWidth = 2;

        ctx.beginPath();
        ctx.arc(95, 50, 40, 0, 2 * Math.PI);
        ctx.stroke();
    }

    socket.subscribe(id, (data: string) => {
        render(canvasRef);
    });

    useEffect(() => {
        render(canvasRef);
    }, [canvasRef]);

    return (
        <canvas ref={canvasRef} width={100} height={100} style="width: 100%;"></canvas>
    )
};

export default SwerveModules;