/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.rest.api.service;

import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.management.api.ViewRepository;
import io.gravitee.repository.management.model.View;
import io.gravitee.rest.api.model.ViewEntity;
import io.gravitee.rest.api.service.impl.ViewServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Nicolas GERAUD (nicolas.geraud at graviteesource.com)
 * @author GraviteeSource Team
 */
@RunWith(MockitoJUnitRunner.class)
public class ViewService_FindTest {

    @InjectMocks
    private ViewServiceImpl viewService = new ViewServiceImpl();

    @Mock
    private ViewRepository mockViewRepository;

    @Test
    public void shouldDoNothingWithEmptyResult() throws TechnicalException {
        when(mockViewRepository.findAllByEnvironment(any())).thenReturn(emptySet());

        List<ViewEntity> list = viewService.findAll();

        assertTrue(list.isEmpty());
        verify(mockViewRepository, times(1)).findAllByEnvironment(any());
    }

    @Test
    public void shouldFindView() throws TechnicalException {
        View view = mock(View.class);
        when(view.getId()).thenReturn("view-id");
        when(view.getName()).thenReturn("view-name");
        when(view.getDescription()).thenReturn("view-description");
        when(view.getOrder()).thenReturn(1);
        when(view.isHidden()).thenReturn(true);
        when(view.getUpdatedAt()).thenReturn(new Date(1234567890L));
        when(view.getCreatedAt()).thenReturn(new Date(9876543210L));
        when(mockViewRepository.findAllByEnvironment(any())).thenReturn(singleton(view));

        List<ViewEntity> list = viewService.findAll();

        assertFalse(list.isEmpty());
        assertEquals("one element", 1, list.size());
        assertEquals("Id", "view-id", list.get(0).getId());
        assertEquals("Name", "view-name", list.get(0).getName());
        assertEquals("Description", "view-description", list.get(0).getDescription());
        assertEquals("Total APIs", 0, list.get(0).getTotalApis());
        assertEquals("Order", 1, list.get(0).getOrder());
        assertEquals("Hidden", true, list.get(0).isHidden());
        assertEquals("UpdatedAt", new Date(1234567890L), list.get(0).getUpdatedAt());
        assertEquals("CreatedAt", new Date(9876543210L), list.get(0).getCreatedAt());
        verify(mockViewRepository, times(1)).findAllByEnvironment(any());
    }
}
