import './home.scss';

import React from 'react';
import { Row, Col } from 'reactstrap';
import UploadFile from 'app/components/Upload/upload';

export const Home = () => {
  return (
    <Row>
      <Col className="pad">
        <h1>Bem vindo ao Facilita Doeal</h1>
        <p className="subTitle">Desde 2022 facilitando a sua vida!</p>
        <p>Abaixo um video explicando o funcionamento do app!</p>
        <div id="videoContainer">
          <iframe
            width="560"
            height="315"
            src="https://www.youtube.com/embed/dQw4w9WgXcQ"
            allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
          ></iframe>
        </div>
      </Col>
      <Col>
        <br />
        <h5>
          Os arquivos do Diário Oficial do Estado de Alagoas (DOEAL)
          <br /> podem ser visualizados e baixados <a href="https://www.jusbrasil.com.br/diarios/DOEAL/">aqui</a>
        </h5>
      </Col>
    </Row>
  );
};

export default Home;
