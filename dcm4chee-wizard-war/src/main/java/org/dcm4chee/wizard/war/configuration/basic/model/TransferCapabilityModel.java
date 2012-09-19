/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is part of dcm4che, an implementation of DICOM(TM) in
 * Java(TM), hosted at http://sourceforge.net/projects/dcm4che.
 *
 * The Initial Developer of the Original Code is
 * Agfa-Gevaert AG.
 * Portions created by the Initial Developer are Copyright (C) 2008
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 * See listed authors below.
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */

package org.dcm4chee.wizard.war.configuration.basic.model;

import java.io.Serializable;
import java.util.Iterator;

import org.dcm4che.conf.api.ConfigurationException;
import org.dcm4che.net.ApplicationEntity;
import org.dcm4che.net.TransferCapability;
import org.dcm4che.net.TransferCapability.Role;
import org.dcm4chee.wizard.war.configuration.basic.tree.DeviceTreeProvider;

/**
 * @author Robert David <robert.david@agfa.com>
 */
public class TransferCapabilityModel implements Serializable, ConfigurationNodeModel {

	private static final long serialVersionUID = 1L;
	
	public static String cssClass = "transfer-capability";
	public static String toolTip = "Transfer Capability";

	private String aeTitle;

	private String sopClass;
	private Role role;
	
	private transient TransferCapability transferCapability;

	public TransferCapabilityModel(TransferCapability transferCapability, String aeTitle) {
		this.aeTitle = aeTitle;
		this.sopClass = transferCapability.getSopClass();
		this.role = transferCapability.getRole();
		this.transferCapability = transferCapability;
	}
	
	public TransferCapability getTransferCapability() {
		return transferCapability;
	}
	
	public void rebuild(ApplicationEntity applicationEntity) throws ConfigurationException {
		if (applicationEntity == null)
			applicationEntity = DeviceTreeProvider.get().getDicomConfigurationProxy()
				.getApplicationEntity(aeTitle);
		if (transferCapability == null) {
			Iterator<TransferCapability> i = applicationEntity.getTransferCapabilities().iterator();
			while (i.hasNext()) {
				TransferCapability transferCapability = i.next();
				if (this.sopClass.equals(transferCapability.getSopClass())
						&& this.role.equals(transferCapability.getRole())) {
					this.transferCapability = transferCapability;
					return;
				}
			}
		}
	}
}