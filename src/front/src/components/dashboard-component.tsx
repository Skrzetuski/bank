import React, {useState} from "react";
import authService from "../services/auth-service";
import { Button } from "react-bootstrap";

function DashboardComponent(){


    const [currentUser, setCurrentUser] = useState(authService.getUser()); 



    return(
    <Button>
        Pulpit
    </Button>)
}

export default DashboardComponent;