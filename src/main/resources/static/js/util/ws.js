import SockJS from 'sockjs-client'
import {Stomp} from '@stomp/stompjs'

let stompClient = null;
const handlers = [];

export function connect() {
    const socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.debug = () => {};
    stompClient.connect({}, frame => {
        handlers.forEach(h => stompClient.subscribe(h.id, message =>
            h.handler(JSON.parse(message.body))
        ));
    });
}

export function addHandler(id, handler) {
    handlers.push({ id, handler });
}

export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }

    console.log('Disconnected')
}
