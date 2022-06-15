import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pdf.reducer';

export const PDFDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const pDFEntity = useAppSelector(state => state.pDF.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pDFDetailsHeading">
          <Translate contentKey="doealDataminingApp.pDF.detail.title">PDF</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{pDFEntity.id}</dd>
          <dt>
            <span id="pdf">
              <Translate contentKey="doealDataminingApp.pDF.pdf">Pdf</Translate>
            </span>
          </dt>
          <dd>
            {pDFEntity.pdf ? (
              <div>
                {pDFEntity.pdfContentType ? (
                  <a onClick={openFile(pDFEntity.pdfContentType, pDFEntity.pdf)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {pDFEntity.pdfContentType}, {byteSize(pDFEntity.pdf)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/pdf" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pdf/${pDFEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PDFDetail;
