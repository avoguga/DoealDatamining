import './extraction.scss';

import React from 'react';
import { Row, Col } from 'reactstrap';
import UploadFile from 'app/components/Upload/upload';

export const Extraction = () => {
  return (
    <Row>
      <Col className="pad">
        <h1 className="titleInput">Por favor, selecione um PDF </h1>
        <p>
          Depois de escolher o arquivo, clique em upload para enviar o arquivo selecionado.
          <br />O arquivo terá seu texto minerado e poderá ser visualizado abaixo
        </p>
        <UploadFile />
        <p>Após a mineração os dados também ficarão salvos em Dados Obtidos</p>
      </Col>
    </Row>
  );
};

export default Extraction;
