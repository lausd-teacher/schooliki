<!DOCTYPE ui:UiBinder SYSTEM "http://dl.google.com/gwt/DTD/xhtml.ent">
<ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	xmlns:g="urn:import:com.google.gwt.user.client.ui"
	xmlns:m="urn:import:gwt.material.design.client.ui"
	xmlns:v="urn:import:net.videmantay.roster.incident">
	<ui:style>
	
	</ui:style>
	<g:HTMLPanel>
		<m:MaterialTitle title="Manage  classroom expectations by creating positive and negative incidents" description="Incidents are events you want to encourage like 'Turned in Homework', 'Shared with a friend'" />
		<!-- Show the same grid as the dashboard  sorted alphabetically-->
		<m:MaterialCard>
			<m:MaterialCardContent>
				<m:MaterialTab ui:field="tab" indicatorColor="green">
					<m:MaterialTabItem  waves="GREEN"><m:MaterialAnchorButton ui:field="posTab" type="FLAT" textColor="green darken-1" text="Superb Decision" href="#posIncidentGrid"/></m:MaterialTabItem>
					<m:MaterialTabItem waves="RED"><m:MaterialAnchorButton ui:field="negTab" type="FLAT" textColor="red darken-1" text="Poor Judgement" href="#negIncidentGrid"/></m:MaterialTabItem>
				</m:MaterialTab>
				<m:MaterialRow ui:field="incidentGirdPanel">
					<m:MaterialPanel ui:field="posIncidentGrid" addStyleNames="posIncidentGrid" m:id="posIncidentGrid">
						<m:MaterialRow ui:field="posIncidentRow">
						</m:MaterialRow>
					</m:MaterialPanel>
					<m:MaterialPanel ui:field="negIncidentGrid" addStyleNames="negIncidentGrid" m:id="negIncidentGrid">
						<m:MaterialRow ui:field="negIncidentRow">
						</m:MaterialRow>
					</m:MaterialPanel>
				</m:MaterialRow>
			</m:MaterialCardContent>
		</m:MaterialCard>
		<m:MaterialFAB ui:field="addIncidentFAB">
			<m:MaterialAnchorButton type="RAISED" iconType="ADD"/>
		</m:MaterialFAB>
		<v:IncidentForm ui:field="incidentForm" />
	  <!-- <v:DeleteIncidentModal ui:field="deleteModal" /> -->
	</g:HTMLPanel>
</ui:UiBinder> 