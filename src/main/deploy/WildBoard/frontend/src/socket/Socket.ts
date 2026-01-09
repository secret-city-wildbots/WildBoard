export type EventHandler<T = any> = (data: T) => void;
type AnyEventHandler = (event: string, data: any) => void;

interface OutgoingMessage<T = any> {
  event: string;
  data: T;
}

interface IncomingMessage<T = any> {
  event: string;
  data: T;
}

export default class Socket {
  private url: string;
  private ws: WebSocket | null = null;
  private handlers: Record<string, EventHandler[]> = {};
  private anyHandlers: AnyEventHandler[] = [];
  private _connectPromise: Promise<void> | null = null;
  private _connected = false;

  constructor(url: string = `ws://${location.hostname}:${Number(location.port) + 1}`) {
    this.url = url;
    this.connect();
  }

  private connect(): void {
    this.ws = new WebSocket(this.url);

    this._connectPromise = new Promise<void>((resolve, reject) => {
      if (!this.ws) return reject(new Error("WebSocket not initialized"));

      this.ws.onopen = () => {
        this._connected = true;
        this.emitLocal("connect");
        resolve();
      };

      this.ws.onerror = (err) => reject(err);

      this.ws.onmessage = (msg: MessageEvent) => {
        try {
          const { event, data } = JSON.parse(msg.data) as IncomingMessage;
          this.emitLocal(event, data);
          this.emitAny(event, data);
        } catch (e) {
          console.error("Invalid WebSocket message:", msg.data);
        }
      };

      this.ws.onclose = () => {
        this._connected = false;
        this.emitLocal("disconnect");
      };
    });
  }

  /** Waits until the WebSocket connection is established */
  public async waitForConnection(): Promise<void> {
    if (this._connected) return;
    if (!this._connectPromise) throw new Error("WebSocket not initialized");
    await this._connectPromise;
  }

  /** Registers an event listener for a specific event */
  public on<T = any>(event: string, handler: EventHandler<T>): void {
    if (!this.handlers[event]) this.handlers[event] = [];
    this.handlers[event].push(handler);
  }

  /** Removes a specific event handler for a given event */
  public off<T = any>(event: string, handler: EventHandler<T>): void {
    if (!this.handlers[event]) return;
    this.handlers[event] = this.handlers[event].filter((h) => h !== handler);
  }

  /** Registers a listener that fires for *any* incoming message */
  public onAny(handler: AnyEventHandler): void {
    this.anyHandlers.push(handler);
  }

  /** Removes a specific "any" handler */
  public offAny(handler: AnyEventHandler): void {
    this.anyHandlers = this.anyHandlers.filter((h) => h !== handler);
  }

  /** Emits an event to the server */
  public emit<T = any>(event: string, data: T): void {
    if (!this._connected || !this.ws) return;
    const msg: OutgoingMessage<T> = { event, data };
    this.ws.send(JSON.stringify(msg));
  }

  /** Internal: emit to event-specific handlers */
  private emitLocal(event: string, data?: any): void {
    (this.handlers[event] || []).forEach((fn) => fn(data));
  }

  /** Internal: emit to "any" handlers */
  private emitAny(event: string, data?: any): void {
    this.anyHandlers.forEach((fn) => fn(event, data));
  }
}