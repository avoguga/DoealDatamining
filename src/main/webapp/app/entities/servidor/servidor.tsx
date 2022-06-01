import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IServidor } from 'app/shared/model/servidor.model';
import { getEntities } from './servidor.reducer';

export const Servidor = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const servidorList = useAppSelector(state => state.servidor.entities);
  const loading = useAppSelector(state => state.servidor.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="servidor-heading" data-cy="ServidorHeading">
        <Translate contentKey="doealDataminingApp.servidor.home.title">Servidors</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="doealDataminingApp.servidor.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/servidor/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="doealDataminingApp.servidor.home.createLabel">Create new Servidor</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {servidorList && servidorList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="doealDataminingApp.servidor.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="doealDataminingApp.servidor.nome">Nome</Translate>
                </th>
                <th>
                  <Translate contentKey="doealDataminingApp.servidor.cpf">Cpf</Translate>
                </th>
                <th>
                  <Translate contentKey="doealDataminingApp.servidor.matricula">Matricula</Translate>
                </th>
                <th>
                  <Translate contentKey="doealDataminingApp.servidor.cargo">Cargo</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {servidorList.map((servidor, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/servidor/${servidor.id}`} color="link" size="sm">
                      {servidor.id}
                    </Button>
                  </td>
                  <td>{servidor.nome}</td>
                  <td>{servidor.cpf}</td>
                  <td>{servidor.matricula}</td>
                  <td>{servidor.cargo}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/servidor/${servidor.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/servidor/${servidor.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/servidor/${servidor.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="doealDataminingApp.servidor.home.notFound">No Servidors found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Servidor;
