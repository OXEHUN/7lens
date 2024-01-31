import React from 'react';
import { FaArrowLeft } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const Container = styled.div`
  height: 100vh;
  width: 100vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  border: 10px solid rgb(255, 215, 7);
`;

const Header = styled.header`
  align-self: flex-start;
  margin: 1rem;
`;

const H1 = styled.h1`
  font-weight: bold;
  color: rgb(134, 134, 134);
`

const ContentWrapper = styled.div`
  flex: 1;
  width: 100vw;
  display: flex;
`;

const LeftContent = styled.div`
  width: 50vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
`;

const RightContent = styled.div`
  width: 50vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;

  form {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

  input, button {
    background-color: rgb(255, 241, 165);
    border: none;
    border-radius: 1rem;
    margin: 0.5rem;
    padding: 0.5rem;
  }

  p {
    margin: 0.5rem;
  }
`;

const LogoImage = styled.img`
  height: 20vh;
`;

const InfoSpan = styled.span`
  background-color: rgb(255, 241, 165);
  padding: 0.5rem;
  border-radius: 1rem;
`;

const Register = () => {

  const signUp = function (payload) {
    const { username, password1, password2 } = payload

    axios({
      method: 'post',
      url: 'http://127.0.0.1:8000/accounts/signup/',
      data: {
        username, password1, password2
      }
    })
      .then((res) => {
        
        console.log(res.data)
        // token.value = res.data.key
        router.push({ name: 'login' })
        window.alert('회원가입을 축하합니다.')
      })
      .catch((err) => {
        console.log(err)
        if(password1 != password2){
          window.alert('비밀번호가 다릅니다.')
        }
        else{
          window.alert('특수문자 및 영문자와 숫자를 섞어 주세요.')
        }
      })
  }

  return (
    <Container>
      <Header>
        <Link to={'/'}>
          <FaArrowLeft />
        </Link>
      </Header>
      <H1>로그인</H1>
      <ContentWrapper>
        <LeftContent>
          <LogoImage src="./7lans_logo.png" alt="" />
          <InfoSpan>
            <h7>
              봉사자와 피봉사자의 연결을 도와주는 보조 웹 사이트
            </h7>
          </InfoSpan>
        </LeftContent>
        <RightContent>
          <form action="submit">
            <input type="text" placeholder='email' />
            <input type="password" placeholder='password' />
            <p>아이디 찾기 | 비밀번호 찾기 | 회원가입</p>
            <button type='submit'>로그인</button>
          </form>
        </RightContent>
      </ContentWrapper>
    </Container>
  );
}

export default Register;
