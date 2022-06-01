import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IConcessao } from 'app/shared/model/concessao.model';
import { getEntities as getConcessaos } from 'app/entities/concessao/concessao.reducer';
import { IServidor } from 'app/shared/model/servidor.model';
import { getEntity, updateEntity, createEntity, reset } from './servidor.reducer';

export const ServidorUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const concessaos = useAppSelector(state => state.concessao.entities);
  const servidorEntity = useAppSelector(state => state.servidor.entity);
  const loading = useAppSelector(state => state.servidor.loading);
  const updating = useAppSelector(state => state.servidor.updating);
  const updateSuccess = useAppSelector(state => state.servidor.updateSuccess);
  const handleClose = () => {
    props.history.push('/servidor');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getConcessaos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...servidorEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...servidorEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="doealDataminingApp.servidor.home.createOrEditLabel" data-cy="ServidorCreateUpdateHeading">
            <Translate contentKey="doealDataminingApp.servidor.home.createOrEditLabel">Create or edit a Servidor</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="servidor-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('doealDataminingApp.servidor.nome')}
                id="servidor-nome"
                name="nome"
                data-cy="nome"
                type="text"
              />
              <ValidatedField label={translate('doealDataminingApp.servidor.cpf')} id="servidor-cpf" name="cpf" data-cy="cpf" type="text" />
              <ValidatedField
                label={translate('doealDataminingApp.servidor.matricula')}
                id="servidor-matricula"
                name="matricula"
                data-cy="matricula"
                type="text"
              />
              <ValidatedField
                label={translate('doealDataminingApp.servidor.cargo')}
                id="servidor-cargo"
                name="cargo"
                data-cy="cargo"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/servidor" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ServidorUpdate;
