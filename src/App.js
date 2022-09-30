import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom";

import UsersList from './components/user/UsersList';
import ViewUserDevices from './components/user/ViewUserDevices';
import DevicesList from './components/device/DevicesList';
import ViewDeviceSensor from './components/device/ViewDeviceSensor';
import SensorsList from './components/sensor/SensorsList';
import RecordsList from './components/record/RecordsList';
import ViewUserRecords from './components/record/ViewUserRecords';

import NotFound from './components/NotFound';
import 'bootstrap/dist/css/bootstrap.min.css';
import AddUser from './components/user/AddUser';
import AddDevice from './components/device/AddDevice';
import AddSensor from './components/sensor/AddSensor';
import AddRecord from './components/record/AddRecord';
import AssignDevice from './components/device/AssignDevice';
import AssignSensor from './components/sensor/AssignSensor';

import Admin from './components/Admin';
import Client from './components/Client';
import Chart from './components/Chart';

import Login from './login/Login';


function App() {

  const defaultRoute = window.location.pathname === "/" ? <Redirect to="/login" /> : undefined;

  return (

    < Router >
      <Switch>
        <Route exact path="/login" component={Login} />

        <Route exact path="/admin" component={Admin} />
        <Route exact path="/client" component={Client} />

        <Route exact path="/user" component={UsersList} />
        <Route path="/user/add" component={AddUser} />
        <Route path="/user/edit/:id" component={AddUser} />
        <Route path="/user/view-devices/:id" component={ViewUserDevices} />

        <Route exact path="/device" component={DevicesList} />
        <Route path="/device/add" component={AddDevice} />
        <Route path="/device/edit/:id" component={AddDevice} />
        <Route path="/device/assign/:id" component={AssignDevice} />
        <Route path="/device/view-sensor/:id" component={ViewDeviceSensor} />

        <Route exact path="/sensor" component={SensorsList} />
        <Route path="/sensor/add" component={AddSensor} />
        <Route path="/sensor/edit/:id" component={AddSensor} />
        <Route path="/sensor/assign/:id" component={AssignSensor} />

        <Route exact path="/record" component={RecordsList} />
        <Route path="/record/add" component={AddRecord} />
        <Route path="/record/edit/:id" component={AddRecord} />
        <Route path="/record/user/:id" component={ViewUserRecords} />

        <Route exact path="/chart" component={Chart} />

        <Route path="*" component={NotFound} />

      </Switch>
      {defaultRoute}
    </Router>
  );
}

export default App;