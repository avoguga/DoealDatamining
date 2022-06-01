import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './servidor.reducer';

export const ServidorDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const servidorEntity = useAppSelector(state => state.servidor.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="servidorDetailsHeading">
          <Translate contentKey="doealDataminingApp.servidor.detail.title">Servidor</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{servidorEntity.id}</dd>
          <dt>
            <span id="nome">
              <Translate contentKey="doealDataminingApp.servidor.nome">Nome</Translate>
            </span>
          </dt>
          <dd>{servidorEntity.nome}</dd>
          <dt>
            <span id="cpf">
              <Translate contentKey="doealDataminingApp.servidor.cpf">Cpf</Translate>
            </span>
          </dt>
          <dd>{servidorEntity.cpf}</dd>
          <dt>
            <span id="matricula">
              <Translate contentKey="doealDataminingApp.servidor.matricula">Matricula</Translate>
            </span>
          </dt>
          <dd>{servidorEntity.matricula}</dd>
          <dt>
            <span id="cargo">
              <Translate contentKey="doealDataminingApp.servidor.cargo">Cargo</Translate>
            </span>
          </dt>
          <dd>{servidorEntity.cargo}</dd>
        </dl>
        <Button tag={Link} to="/servidor" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/servidor/${servidorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServidorDetail;
