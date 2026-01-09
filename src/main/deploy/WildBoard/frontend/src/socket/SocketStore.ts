import Socket from "./Socket";
import {EventHandler} from "./Socket";

export default class SocketStore {
    socket: Socket;
    vals: Record<string, any> = {};

    constructor(url: string = `ws://${location.hostname}:${Number(location.port)+1}`) {
        this.socket = new Socket(url);
        
        this.socket.waitForConnection().then(() => {
            this.socket.onAny((event: string, data: any) => {
                this.vals[event] = data;
            })
        });
    }

    pullVal(key: string): any {
        return this.vals[key];
    }

    on(key: string, handler: EventHandler) {
        this.socket.on(key, handler);
    }

    off(key: string, handler: EventHandler) {
        this.socket.off(key, handler);
    }

    pushVal(key: string, data: any) {
        this.socket.emit(key, data);
    }
}