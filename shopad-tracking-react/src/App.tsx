import React, {useEffect, useState} from 'react';
import axios from "axios";
import Header from "./component/Header";
import CategoryHeader from "./component/CategoryHeader";
import ContentsList from "./component/ContentsList";
import {Tracking} from "./model/Tracking";
import Cookies from "js-cookie";

interface AppProps {
    userId: string
    userInTime: Date
}

const App: React.FC<AppProps> = ({userId, userInTime}) => {
    
    // 카테고리 정의 (초기값 HOT)
    const [categoryId, setCategoryId] = useState("00000000");

    //카테고리 클릭할 때 
    useEffect(() => {
        const timeStamp = new Date();
        let startPoint = false;
        
        if(Cookies.get('userState') === undefined) {
            startPoint = true;
        }

        // 쿠키 유효 시간 10분
        let expirationTime = new Date(timeStamp.getTime() + 10 * 60 * 1000);
        Cookies.set('userState', 'shopping', {expires: expirationTime, path:'/'});

        axios.post('http://localhost:8080/trackingUser/api', {
            "description": "OK",
            "data": {
                "user_id": "shopad",
                "action": "categoryClick",
                "action_detail": categoryId,
                "timestamp": String(timeStamp.toISOString().replace('Z', '')),
                "start_point" : startPoint
            }
        })
        .then(function (response) {
            console.log(response);
        })
        .catch(function (error) {
            alert("error = " + error);
            console.log(error);
        });
    }, [categoryId])
    
    // 검색 클릭 카운트 정의
    const [searchClick, setSearchClick] = useState<number>(0);

    // 검색 클릭될 때마다 1씩 증가 
    const searchIncrease = () => {
        setSearchClick(searchClick+1);
        console.log("searchClick "+searchClick);
    }

    // 한 유저의 쇼핑 끝 
    const shoppingEnd = () => {
        console.log("shopping End!");

        const userOutTime = new Date();

        console.log(userOutTime);
    
    }

    return (
        <div id="content" className="trendpick womens sw_content">
            {/* <input />  */}
            {/* <button onClick={searchIncrease}>검색</button> &ensp; */}
            {/* <button onClick={shoppingEnd}>쇼핑 끝내기!</button> */}
            
            <Header />
            <CategoryHeader categoryId={categoryId} setCategoryId={setCategoryId}/>
            <ContentsList categoryId={categoryId} />
        </div>
    );
};

export default App;
