import React, {useEffect, useState} from 'react';
import styled from 'styled-components';
import GameNav from '../../components/navs/GameNav';
import UseOpenViduSession from "../../helpers/useOpenViduSession.jsx";
import VideoChattingLobby from "./VideoChattingLobby.jsx";
import VolunteerGamePage from "./VolunteerGamePage.jsx";

const AppContainer = styled.div`
    height: 100vh;
    //width: 100wh;
    display: flex;
    flex-direction: column;
`;


const VideoChattingPage = () => {
    const { session, mainStreamManager, subscribers, joinSession, renderUserVideoComponent } = UseOpenViduSession();

    const [isGameStarted, setGameStarted] = useState(false);

    useEffect(() => {
        joinSession();
    }, []);

    // FIXME 테스트용 토글 method 이후 지울 것
    const toggleGameStarted = () => {
        setGameStarted(prevState => !prevState);
    };

    return (
        <AppContainer>
            <GameNav/>
            {isGameStarted ? (
                <VolunteerGamePage
                    renderUserVideoComponent={renderUserVideoComponent}
                    mainStreamManager={mainStreamManager}
                    subscribers={subscribers}
                    session={session}
                />
            ) : (
                <VideoChattingLobby
                    renderUserVideoComponent={renderUserVideoComponent}
                    mainStreamManager={mainStreamManager}
                    subscribers={subscribers}
                />
            )}

            {/* 게임 상태를 토글하는 버튼 */}
            <button onClick={toggleGameStarted}>
                {isGameStarted ? 'Stop Game' : 'Start Game'}
            </button>

        </AppContainer>
    );
};

export default VideoChattingPage;