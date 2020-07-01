import React from 'react';
import {Route, Link, Switch, BrowserRouter } from 'react-router-dom';


import Register from "./components/register-component";
import Home from './components/home-component';

const App = () => {
  return (
    <BrowserRouter>
      <div>
        <nav>
          <Link to="/">Home</Link>
          <Link to="/register">Rejestracja</Link>
        </nav>
      </div>
      <div className="container mt-3">
        <Switch>
          <Route exact path="/" component={Home} />
          <Route exact path="/register" component={Register}/>
        </Switch>
      </div>
    </BrowserRouter>
  );
}

export default App;
