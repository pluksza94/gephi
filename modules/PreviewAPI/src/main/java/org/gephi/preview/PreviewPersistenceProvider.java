/*
 Copyright 2008-2011 Gephi
 Authors : Mathieu Bastian
 Website : http://www.gephi.org

 This file is part of Gephi.

 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright 2011 Gephi Consortium. All rights reserved.

 The contents of this file are subject to the terms of either the GNU
 General Public License Version 3 only ("GPL") or the Common
 Development and Distribution License("CDDL") (collectively, the
 "License"). You may not use this file except in compliance with the
 License. You can obtain a copy of the License at
 http://gephi.org/about/legal/license-notice/
 or /cddl-1.0.txt and /gpl-3.0.txt. See the License for the
 specific language governing permissions and limitations under the
 License.  When distributing the software, include this License Header
 Notice in each file and include the License files at
 /cddl-1.0.txt and /gpl-3.0.txt. If applicable, add the following below the
 License Header, with the fields enclosed by brackets [] replaced by
 your own identifying information:
 "Portions Copyrighted [year] [name of copyright owner]"

 If you wish your version of this file to be governed by only the CDDL
 or only the GPL Version 3, indicate your decision by adding
 "[Contributor] elects to include this software in this distribution
 under the [CDDL or GPL Version 3] license." If you do not indicate a
 single choice of license, a recipient has the option to distribute
 your version of this file under either the CDDL, the GPL Version 3 or
 to extend the choice of license to its licensees as provided above.
 However, if you add GPL Version 3 code and therefore, elected the GPL
 Version 3 license, then the option applies only if the new code is
 made subject to such option by the copyright holder.

 Contributor(s):

 Portions Copyrighted 2011 Gephi Consortium.
 */
package org.gephi.preview;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.gephi.preview.api.PreviewController;
import org.gephi.project.api.Workspace;
import org.gephi.project.spi.WorkspacePersistenceProvider;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Mathieu Bastian
 */
@ServiceProvider(service = WorkspacePersistenceProvider.class)
public class PreviewPersistenceProvider implements WorkspacePersistenceProvider {

    @Override
    public void writeXML(XMLStreamWriter writer, Workspace workspace) {
        PreviewModelImpl model = workspace.getLookup().lookup(PreviewModelImpl.class);
        if (model != null) {
            try {
                model.writeXML(writer);
            } catch (XMLStreamException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void readXML(XMLStreamReader reader, Workspace workspace) {
        PreviewModelImpl model = (PreviewModelImpl) Lookup.getDefault().lookup(PreviewController.class).getModel(workspace);
        if (model == null) {
            model = new PreviewModelImpl(workspace);
            workspace.add(model);
        }
        try {
            model.readXML(reader);
        } catch (XMLStreamException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String getIdentifier() {
        return "previewmodel";
    }
}