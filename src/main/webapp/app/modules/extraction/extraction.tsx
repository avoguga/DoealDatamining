import './extraction.scss';

import React from 'react';
import { Row, Col } from 'reactstrap';
import UploadFile from 'app/components/Upload/upload';

export const Extraction = () => {

  return (
    <Row>
      <Col className="pad">
        <h1 className="titleInput">Por favor, selecione um PDF </h1>
        <p>Depois de dado o upload, o arquivo será minerado e poderá ser visualizado em Dados Obtidos</p>
        <UploadFile />
      </Col>
    </Row>
  );
};

export default Extraction;
