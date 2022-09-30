import axios from "axios";

export default axios.create({
    //baseURL: 'http://localhost:8080/',
    baseURL: "https://spring2-sd2021-kaj.herokuapp.com/",
    headers: {
        'Content-Type': 'application/json'
    }
})