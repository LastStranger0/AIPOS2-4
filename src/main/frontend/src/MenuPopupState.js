import React from 'react';
import Button from '@material-ui/core/Button';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import PopupState, { bindTrigger, bindMenu } from 'material-ui-popup-state';
import {Link} from "react-router-dom";

export default function MenuPopupState() {

    return (
        <PopupState variant="popover" popupId="demo-popup-menu">
            {(popupState) => (
                <React.Fragment>
                    <Button variant="contained" color="primary" {...bindTrigger(popupState)}>
                        Open Menu
                    </Button>
                    <Menu {...bindMenu(popupState)}>
                        <MenuItem component={Link} to="/Users">User</MenuItem>
                        <MenuItem component={Link} to="/Games">Games</MenuItem>
                        <MenuItem component={Link} to="/Review">Review</MenuItem>
                        <MenuItem component={Link} to="/Keys">Keys</MenuItem>
                    </Menu>
                </React.Fragment>
            )}
        </PopupState>
    );
}

function onSubmit (){

}


