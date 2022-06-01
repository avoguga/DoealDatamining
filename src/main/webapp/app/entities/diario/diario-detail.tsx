import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './diario.reducer';

export const DiarioDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const diarioEntity = useAppSelector(state => state.diario.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="diarioDetailsHeading">
          <Translate contentKey="doealDataminingApp.diario.detail.title">Diario</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{diarioEntity.id}</dd>
          <dt>
            <span id="dataPublicacao">
              <Translate contentKey="doealDataminingApp.diario.dataPublicacao">Data Publicacao</Translate>
            </span>
          </dt>
          <dd>
            {diarioEntity.dataPublicacao ? (
              <TextFormat value={diarioEntity.dataPublicacao} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="ano">
              <Translate contentKey="doealDataminingApp.diario.ano">Ano</Translate>
            </span>
          </dt>
          <dd>{diarioEntity.ano}</dd>
          <dt>
            <span id="numero">
              <Translate contentKey="doealDataminingApp.diario.numero">Numero</Translate>
            </span>
          </dt>
          <dd>{diarioEntity.numero}</dd>
        </dl>
        <Button tag={Link} to="/diario" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/diario/${diarioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DiarioDetail;
