import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/user');
}

const create = data => {
    return httpClient.post("/user", data);
}

const get = id => {
    return httpClient.get(`/user/${id}`);
}

const getbyusername = username => {
    return httpClient.get(`/useru/${username}`);
}

const update = data => {
    return httpClient.put('/user', data);
}

const remove = id => {
    return httpClient.delete(`/user/${id}`);
}

const getUserDevices = id => {
    return httpClient.get(`/user/device/${id}`);
}

const logger = { getAll, create, get, getbyusername, update, remove, getUserDevices };
export default logger;