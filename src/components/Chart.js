import { useEffect, useState, React } from 'react';
import { Link } from 'react-router-dom';
import { MDBContainer } from "mdbreact";
import { Bar } from "react-chartjs-2";
import recordService from '../services/record.service.js';

const Chart = () => {

  const [energyList, setEnergyList] = useState([]);
  const [viewEnergyInSelectedDay, setViewEnergyInSelectedDay] = useState([]);
  const [date, setDate] = useState('');

  useEffect(() => {
    selectedEnergCons(sessionStorage.getItem("USER_ID"), date);
    viewEnergyInSelectedDayy();
  }, []);


  const selectedEnergCons = (idClient, date) => {
    recordService.getChart(idClient, date)
      .then(response => {
        console.log("Chart SUCCESS");
        console.log(response.data);
        setEnergyList(response.data);
      })
      .catch(error => {
        console.log("No consumtion for the selected date by client with id:" + idClient);
      })
  };


  const viewEnergyInSelectedDayy = () => {
    const data_role = sessionStorage.getItem("USER");
    const data_id = sessionStorage.getItem("USER_ID");

    console.log("data role = " + data_role);
    console.log("data id = " + data_id);

    if (data_role === "CLIENT") {
      console.log("date view = " + date);
      selectedEnergCons(data_id, date);
      setViewEnergyInSelectedDay(!viewEnergyInSelectedDay);
      console.log("viewEnergyInSelectedDay");
      console.log(viewEnergyInSelectedDay);
    }
    else {
      return <div>
        You need to login as a Client.
      </div>;
    };
  }

  // Sample data
  const data = {
    labels: ["12:00AM", "1:00AM", "2:00AM", "3:00AM", "4:00AM", "5:00AM", "6:00AM", "7:00AM", "8:00AM", "9:00AM", "10:00AM", "11:00AM",
      "12:00PM", "1:00PM", "2:00PM", "3:00PM", "4:00PM", "5:00PM", "6:00PM", "7:00PM", "8:00PM", "9:00PM", "10:00PM", "11:00PM"],
    datasets: [
      {
        label: "Daily Energy Consumption",
        data: [energyList[0], energyList[1], energyList[2], energyList[3], energyList[4], energyList[5],
               energyList[6], energyList[7], energyList[8], energyList[9], energyList[10], energyList[11],
               energyList[12], energyList[13], energyList[14], energyList[15], energyList[16], energyList[17],
               energyList[18], energyList[19], energyList[20], energyList[21], energyList[22], energyList[23]],
        backgroundColor: "#02b844",
        borderWidth: 1,
        borderColor: "#000000",
      }
    ]
  }

  return (



    <div>
      <h3>Energy Consumption Chart</h3>

      <div>
        <p>.</p>
      </div>

      <div className="row">
        <div className="col s6">
          <div className="input-field col s11">
            <i className="material-icons prefix">Date: </i>
            <input onChange={(e) => setDate(e.target.value)}
              value={date} type="date" id="autocomplete-input" className="autocomplete" />
          </div>
        </div>

        <div className="col s7">
          <button onClick={() => viewEnergyInSelectedDayy()} variant="outlined">
            Confirm
          </button>
          {/* <button onClick={() => ()} variant="outlined">
            Confirm
          </button> */}
        </div>
      </div>

      <div>
        <p>.</p>
      </div>

      <div>
        <MDBContainer>
          <Bar data={data}
            style={{ maxHeight: '600px' }}
          />
        </MDBContainer>
      </div>
      <Link to="/client" className="btn btn-link mb-2">Back to Options</Link>
    </div>

  );
}

export default Chart;