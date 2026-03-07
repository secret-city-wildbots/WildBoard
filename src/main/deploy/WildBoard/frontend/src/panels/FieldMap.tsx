import { h } from "preact";
import { useEffect, useRef } from "preact/hooks";
import { WsEventBus } from "../ws/WSEventBus";

interface Props {
    socket: WsEventBus;
    id: number;

    alliance: boolean; // true for red, false for blue
}

export default function FieldMap({ socket, id, alliance }: Props) {
    const containerRef = useRef<HTMLDivElement>(null);
    const canvasRef = useRef<HTMLCanvasElement>(null);
    const imgRef = useRef<HTMLImageElement>(null);

    // ------------------------------
    // CONFIGURE FIELD OFFSETS HERE
    // ------------------------------
    // Original image pixels to crop (source image)
    const crop = {
        left: 20,   // pixels to ignore on left
        right: 20,  // pixels to ignore on right
        top: 68,    // pixels to ignore on top
        bottom: 62, // pixels to ignore on bottom
    };
    // ------------------------------

    const FIELD_W = 8.02;
    const FIELD_H = 16.54;
    const ROBOT_SIZE = 0.9;

    let targetPose = { x: 0, y: 0, theta: 0 };
    let currentPose = { x: 0, y: 0, theta: 0 };
    const trail: { x: number; y: number }[] = [];

    const draw = () => {
        const canvas = canvasRef.current;
        const container = containerRef.current;
        const img = imgRef.current;
        if (!canvas || !container || !img) return;

        // match canvas to container
        const rect = container.getBoundingClientRect();
        canvas.width = rect.width;
        canvas.height = rect.height;

        const ctx = canvas.getContext("2d");
        if (!ctx) return;

        // compute scaling from original image to displayed size
        const displayedWidth = rect.width;
        const displayedHeight = rect.height;

        const scaleX = displayedWidth / img.naturalWidth;
        const scaleY = displayedHeight / img.naturalHeight;

        const scaledCrop = {
            left: crop.left * scaleX,
            right: crop.right * scaleX,
            top: crop.top * scaleY,
            bottom: crop.bottom * scaleY,
        };

        const usableWidth = displayedWidth - scaledCrop.left - scaledCrop.right;
        const usableHeight = displayedHeight - scaledCrop.top - scaledCrop.bottom;

        const scale = Math.min(usableWidth / FIELD_W, usableHeight / FIELD_H);

        const offsetX = scaledCrop.left + (usableWidth - FIELD_W * scale) / 2;
        const offsetY = scaledCrop.top + (usableHeight - FIELD_H * scale) / 2;

        // interpolate
        currentPose.x += (targetPose.x - currentPose.x) * 0.15;
        currentPose.y += (targetPose.y - currentPose.y) * 0.15;
        currentPose.theta += (targetPose.theta - currentPose.theta) * 0.15;

        // trail
        trail.push({ x: currentPose.x, y: currentPose.y });
        if (trail.length > 200) trail.shift();

        ctx.clearRect(0, 0, canvas.width, canvas.height);

        // draw trail
        ctx.beginPath();
        trail.forEach((p, i) => {
            const px = offsetX + p.x * scale;
            const py = offsetY + FIELD_H * scale - p.y * scale;
            if (i === 0) ctx.moveTo(px, py);
            else ctx.lineTo(px, py);
        });
        ctx.strokeStyle = "rgba(0,255,0,0.5)";
        ctx.lineWidth = 2;
        ctx.stroke();

        // draw robot
        const px = offsetX + currentPose.x * scale;
        const py = offsetY + FIELD_H * scale - currentPose.y * scale;
        const size = ROBOT_SIZE * scale;

        ctx.save();
        ctx.translate(px, py);
        ctx.rotate(-currentPose.theta * Math.PI / 180);

        ctx.fillStyle = "#00ff00";
        ctx.strokeStyle = "#fff";
        ctx.lineWidth = 2;

        ctx.fillRect(-size / 2, -size / 2, size, size);
        ctx.strokeRect(-size / 2, -size / 2, size, size);

        // heading
        ctx.beginPath();
        ctx.moveTo(0, 0);
        ctx.lineTo(size / 2 + 10, 0);
        ctx.stroke();

        ctx.restore();

        requestAnimationFrame(draw);
    };

    useEffect(() => {
        requestAnimationFrame(draw);
    }, []);

    useEffect(() => {
        const unsubscribe = socket.subscribe(id, (updated: string) => {
            const parts = updated.trim().split(",");
            if (parts.length >= 3) {
                targetPose = {
                    x: parseFloat(parts[0]),
                    y: parseFloat(parts[1]),
                    theta: parseFloat(parts[2]),
                };
            }
        });
        return () => unsubscribe();
    }, [socket, id]);

    return (
        <div style="width: 100%; height: 100%; display: flex; flex-direction: column; align-items: center; display: flex;
    min-height: 0;">
            <label>Field</label>
            <div ref={containerRef} class="field-map">
                <img
                    ref={imgRef}
                    src={"/assets/fieldmap" + (alliance ? "red" : "blue") + ".png"}
                    class="field-map-img"
                />
                <canvas ref={canvasRef} class="field-map-canvas" />
            </div>
        </div>
    );
}