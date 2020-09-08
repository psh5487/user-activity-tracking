/* eslint-disable */

import React, {useState} from 'react';

interface searchClickProps {
    search_click_state?: number; //making it optional from mandatory
    onCreate(data: any): void;
}

const Search: React.FC <searchClickProps> = (props) => {

    let search_click_state = 0;

    const onSubmitForm = (e:any) => {
        e.preventDefault();

        useState({
            search_click_state: search_click_state + 1,
        })
        
        props.onCreate(search_click_state);

        console.log("search button cliked!");
    };

    return (
        <React.Fragment>
            <form onSubmit={onSubmitForm}>
                <input /> 
                <button id="button" className="button0">검색</button>
            </form>
        </React.Fragment>
    );

}

export default Search;