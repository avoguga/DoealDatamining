import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IServidor } from 'app/shared/model/servidor.model';
import { getEntities as getServidors } from 'app/entities/servidor/servidor.reducer';
import { IDiario } from 'app/shared/model/diario.model';
import { getEntities as getDiarios } from 'app/entities/diario/diario.reducer';
import { IConcessao } from 'app/shared/model/concessao.model';
import { getEntity, updateEntity, createEntity, reset } from './concessao.reducer';

export const ConcessaoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const servidors = useAppSelector(state => state.servidor.entities);
  const diarios = useAppSelector(state => state.diario.entities);
  const concessaoEntity = useAppSelector(state => state.concessao.entity);
  const loading = useAppSelector(state => state.concessao.loading);
  const updating = useAppSelector(state => state.concessao.updating);
  const updateSuccess = useAppSelector(state => state.concessao.updateSuccess);
  const handleClose = () => {
    props.history.push('/concessao');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getServidors({}));
    dispatch(getDiarios({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...concessaoEntity,
      ...values,
      servidor: servidors.find(it => it.id.toString() === values.servidor.toString()),
      diario: diarios.find(it => it.id.toString() === values.diario.toString()),
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
          ...concessaoEntity,
          servidor: concessaoEntity?.servidor?.id,
          diario: concessaoEntity?.diario?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="doealDataminingApp.concessao.home.createOrEditLabel" data-cy="ConcessaoCreateUpdateHeading">
            <Translate contentKey="doealDataminingApp.concessao.home.createOrEditLabel">Create or edit a Concessao</Translate>
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
                  id="concessao-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('doealDataminingApp.concessao.dataAssinatura')}
                id="concessao-dataAssinatura"
                name="dataAssinatura"
                data-cy="dataAssinatura"
                type="date"
              />
              <ValidatedField
                label={translate('doealDataminingApp.concessao.portaria')}
                id="concessao-portaria"
                name="portaria"
                data-cy="portaria"
                type="text"
              />
              <ValidatedField
                label={translate('doealDataminingApp.concessao.periodoAquisitivo')}
                id="concessao-periodoAquisitivo"
                name="periodoAquisitivo"
                data-cy="periodoAquisitivo"
                type="text"
              />
              <ValidatedField
                label={translate('doealDataminingApp.concessao.tempoAfastamento')}
                id="concessao-tempoAfastamento"
                name="tempoAfastamento"
                data-cy="tempoAfastamento"
                type="text"
              />
              <ValidatedField
                label={translate('doealDataminingApp.concessao.dataInicio')}
                id="concessao-dataInicio"
                name="dataInicio"
                data-cy="dataInicio"
                type="date"
              />
              <ValidatedField
                label={translate('doealDataminingApp.concessao.dataFinal')}
                id="concessao-dataFinal"
                name="dataFinal"
                data-cy="dataFinal"
                type="date"
              />
              <ValidatedField
                id="concessao-servidor"
                name="servidor"
                data-cy="servidor"
                label={translate('doealDataminingApp.concessao.servidor')}
                type="select"
              >
                <option value="" key="0" />
                {servidors
                  ? servidors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="concessao-diario"
                name="diario"
                data-cy="diario"
                label={translate('doealDataminingApp.concessao.diario')}
                type="select"
              >
                <option value="" key="0" />
                {diarios
                  ? diarios.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/concessao" replace color="info">
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

export default ConcessaoUpdate;
