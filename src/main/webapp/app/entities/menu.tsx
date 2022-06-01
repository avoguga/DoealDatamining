import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/diario">
        <Translate contentKey="global.menu.entities.diario" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/servidor">
        <Translate contentKey="global.menu.entities.servidor" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/concessao">
        <Translate contentKey="global.menu.entities.concessao" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/pdf">
        <Translate contentKey="global.menu.entities.pdf" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
