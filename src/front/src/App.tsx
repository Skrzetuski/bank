import React, { useEffect, useState } from 'react';
import {Route, Link, Switch, BrowserRouter } from 'react-router-dom';
import { Navbar, Nav } from 'react-bootstrap';

import Register from "./components/register-component";
import Home from './components/home-component';
import authService from './services/auth-service';
import DashboardComponent from './components/dashboard-component';
import Login from './components/login-component';
import InfoComponent from './components/info-component';
import TransferComponent from './components/transfer-component';

const App = () => {


  const [user, setUser] = useState("");

  const logout = () => {
    authService.logout();
    window.location.reload();
  }

  useEffect(()=>{
    const user = authService.getUser();
    if(user){
        setUser(user);
    }
  },[])



  return (
    <BrowserRouter>
      <div>
        <nav>
        <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
          <Navbar.Brand href="/">Abelo Bank</Navbar.Brand>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse>
            <Nav className="mr-auto">

            {user ? (
                <>
                <Nav.Link><Link className="text-white" to="/dashboard">Pulpit</Link></Nav.Link>
                <Nav.Link><Link className="text-white" to="/transfer">Przelew</Link></Nav.Link>
                <Nav.Link><Link className="text-white" to="/info">Informacje</Link></Nav.Link>

                <Nav className=" my-2 my-lg-0">
                  <Nav.Link href="/login" onClick={logout} >Wyloguj</Nav.Link>
                </Nav>
                </>
            ) : (
              <>
              <Nav.Link href="/login">Zaloguj</Nav.Link>
              <Nav.Link href="/register">Rejestracja</Nav.Link>
              </>
            )}

            
            </Nav>
          </Navbar.Collapse>
        </Navbar>

        </nav>
      </div>
      <div className="d-flex justify-content-center">
        <Switch>
            {user ? (
              
              <>
                <Route exact path="/" component={Home} />
                <Route exact path="/dashboard" component={DashboardComponent}/>
                <Route exact path="/transfer" component={TransferComponent}/>
                <Route exact path="/info" component={InfoComponent}/>
                <Route exact path="/login" component={Login}/>
              </>
                       
            ) : (
              <>
              <Route exact path="/" component={Home} />
              <Route exact path="/register" component={Register}/>
              <Route exact path="/login" component={Login}/>
            </>
            )}
        </Switch>
      </div>
    </BrowserRouter>
  );
}

export default App;
