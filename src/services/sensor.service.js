import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/sensor');
}

const create = data => {
    return httpClient.post("/sensor", data);
}

const get = id => {
    return httpClient.get(`/sensor/${id}`);
}

const update = data => {
    return httpClient.put('/sensor', data);
}

const remove = id => {
    return httpClient.delete(`/sensor/${id}`);
}

const assoc = (deviceId, sensorId) => {
    return httpClient.put(`/device/sensor/${deviceId}/${sensorId}`);
}

const logger = { getAll, create, get, update, remove, assoc };
export default logger;