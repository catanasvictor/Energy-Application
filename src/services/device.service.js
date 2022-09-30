import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/device');
}

const create = data => {
    return httpClient.post("/device", data);
}

const get = id => {
    return httpClient.get(`/device/${id}`);
}

const update = data => {
    return httpClient.put('/device', data);
}

const remove = id => {
    return httpClient.delete(`/device/${id}`);
}

const assoc = (userId, deviceId) => {
    return httpClient.put(`/user/device/${userId}/${deviceId}`);
}

const getDeviceSensor = id => {
    return httpClient.get(`/device/sensor/${id}`);
}

const logger = { getAll, create, get, update, remove, assoc, getDeviceSensor };
export default logger;