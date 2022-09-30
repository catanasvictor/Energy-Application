import { useEffect, useState } from 'react';
import { Link, useHistory, useParams } from 'react-router-dom';
import recordService from '../../services/record.service';
import * as SockJS from "sockjs-client";
import * as Stomp from "stompjs";

const ViewUserRecords = () => {

  const [record, setRecord] = useState([]);
  const history = useHistory();
  const user_id = sessionStorage.getItem("USER_ID");

  const init = () => {
    recordService.getByUser(user_id)
      .then(response => {
        console.log('Printing records data', response.data);
        setRecord(response.data);
      })
      .catch(error => {
        console.log('Something went wrong', error);
      })
  }

  const notify = () => {
    const URL = "https://spring2-sd2021-kaj.herokuapp.com/websocket";
    //const URL = "http://localhost:8080/websocket";
    const socket = new SockJS(URL);
    const stomp = Stomp.over(socket);
    stomp.connect({}, frame => {
      console.log("Websocket connected to " + frame);
      stomp.subscribe("/broker/websocket/record", notification => {
        let message = notification.body;
        console.log(message);
        alert(message);
      })
    })
  }

  useEffect(() => {
    init();
    notify();
  }, []);

  const redirect = () => {
    if (sessionStorage.getItem("USER") === "ADMIN")
      history.push('/admin');
    else {
      history.push('/client');
    }
  }
  function convertUTCDateToLocalDate(date) {
    var newDate = new Date(date);
    return newDate.toString();
  }

    return (
        //init(),
        <div className="container">
          <h3>List of Records</h3>
          <hr />
          <div>
            <table className="table table-bordered table-striped">
              <thead className="thead-dark">
                <tr>
                  <th>Timestamp</th>
                  <th>Energy Consumption</th>
                </tr>
              </thead>
              <tbody>
                {//conversion from timestamp to localTime
                  record.map(record => (
                    <tr key={record.id}>
                      <td>{convertUTCDateToLocalDate(record.timestamp)}</td>
                      <td>{record.energyConsumption}</td>
                    </tr>
                  ))
                }
              </tbody>
            </table>
            <Link className="btn btn-link mb-2" onClick={() => { redirect(); }}>Back to Options</Link>
          </div>
        </div>
      );
    }

export default ViewUserRecords;