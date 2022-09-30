import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/record');
}

const create = data => {
    return httpClient.post("/record", data);
}

const get = id => {
    return httpClient.get(`/record/${id}`);
}

const update = data => {
    return httpClient.put('/record', data);
}

const remove = id => {
    return httpClient.delete(`/record/${id}`);
}

const getChart = (id, dataa) => {
    return httpClient.get(`/chart/${id}/${dataa}`);
}

const getByUser = id => {
    return httpClient.get(`/record/user/${id}`);
}

const logger = { getAll, create, get, update, remove, getChart, getByUser };
export default logger;