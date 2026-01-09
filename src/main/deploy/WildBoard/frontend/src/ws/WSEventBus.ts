export type WsDataHandler = (data: string) => void;

export class WsEventBus {
  private ws: WebSocket;
  private handlers: Map<number, Set<WsDataHandler>> = new Map();
  private connected = false;

  constructor(url: string) {
    this.ws = new WebSocket(url);

    this.ws.onopen = () => {
      this.connected = true;
      console.log("[WS] Connected");
    };

    this.ws.onclose = () => {
      this.connected = false;
      console.warn("[WS] Disconnected");
    };

    this.ws.onerror = (e) => {
      console.error("[WS] Error:", e);
    };

    this.ws.onmessage = (event) => {
      if (typeof event.data !== "string") return;
      this.handleMessage(event.data);
    };
  }

  private handleMessage(msg: string) {
    // Expected format: e[id].[data]
    if (!msg.startsWith("e")) return;

    const dotIndex = msg.indexOf(".");
    if (dotIndex === -1) return;

    const idStr = msg.substring(1, dotIndex);
    const data = msg.substring(dotIndex + 1);

    const id = Number(idStr);
    if (Number.isNaN(id)) return;

    const listeners = this.handlers.get(id);
    if (!listeners) return;

    for (const handler of listeners) {
      handler(data);
    }
  }

  /**
   * Subscribe to messages with a specific ID.
   * Returns an unsubscribe function.
   */
  subscribe(id: number, handler: WsDataHandler): () => void {
    let set = this.handlers.get(id);
    if (!set) {
      set = new Set();
      this.handlers.set(id, set);
    }

    set.add(handler);

    return () => {
      set!.delete(handler);
      if (set!.size === 0) {
        this.handlers.delete(id);
      }
    };
  }

  /**
   * send raw text back to robot
   */
  sendRaw(text: string) {
    if (this.connected) {
      this.ws.send(text);
    }
  }

  close() {
    this.ws.close();
  }
}
