			<#-- Main area -->
    		<section class="content">
				<div class="container-fluid">
				
					<#-- Stepper -->
					<div id="eventStepper" class="bs-stepper bg-white">

						<#-- Header -->
          				<div class="bs-stepper-header" role="tablist">

							<#-- Header 1: Basic Info -->
            				<div class="step" data-target="#eventBasicInfo">
              					<button type="button" class="step-trigger" role="tab" id="eventSteppertrigger1" aria-controls="eventBasicInfo">
                					<span class="bs-stepper-circle">
                  						<span class="fas fa-circle-info" aria-hidden="true"></span>
                					</span>
                					<span class="bs-stepper-label">Basic Info</span>
              					</button>
            				</div>
							<#-- /Header 1: Basic Info -->

							<#-- Header 2: Timing -->
            				<div class="bs-stepper-line"></div>
            				<div class="step" data-target="#eventTiming">
              					<button type="button" class="step-trigger" role="tab" id="eventSteppertrigger2" aria-controls="eventTiming">
                					<span class="bs-stepper-circle">
                  						<span class="fas fa-clock" aria-hidden="true"></span>
                					</span>
                					<span class="bs-stepper-label">Timing</span>
              					</button>
            				</div>
							<#-- /Header 2: Timing -->

							<#-- Header 3: Additional Info -->
							<div class="bs-stepper-line"></div>
            				<div class="step" data-target="#eventAdditionalInfo">
              					<button type="button" class="step-trigger" role="tab" id="eventSteppertrigger1" aria-controls="eventAdditionalInfo">
                					<span class="bs-stepper-circle">
                  						<span class="fas fa-map-pin" aria-hidden="true"></span>
                					</span>
                					<span class="bs-stepper-label">Additional Info</span>
              					</button>
            				</div>
							<#-- /Header 3: Additional Info -->

          				</div>
						<#-- /Header -->

						<#-- Content -->
          				<div class="bs-stepper-content">

							<#-- Content 1: Basic Info -->
							<#assign prev = false>
            				<#assign next = true>
            						
              				<div id="eventBasicInfo" role="tabpanel" class="bs-stepper-pane" aria-labelledby="eventSteppertrigger1">
              					<#include "./addEdit_basicInfo.ftl">	
                				<#include "./stepper_btn.ftl">
              				</div>
							<#-- /Content 1: Basic Info -->

							<#-- Content 2: Timing -->
							<#assign prev = true>
            				<#assign next = true>
              				<div id="eventTiming" role="tabpanel" class="bs-stepper-pane" aria-labelledby="eventSteppertrigger2">
                				<#include "./addEdit_timing.ftl">	
								<#include "./stepper_btn.ftl">
              				</div>
							<#-- /Content 2: Timing -->

							<#-- Content 3: Additional Info -->
							<#assign prev = true>
            				<#assign next = false>
			  				<div id="eventAdditionalInfo" role="tabpanel" class="bs-stepper-pane text-center" aria-labelledby="eventSteppertrigger3">
                				<#include "./addEdit_additionalInfo.ftl">	
								<#include "./stepper_btn.ftl">
              				</div>
            				<#-- /Content 3: Additional Info -->

          				</div>
						<#-- Content -->

        			</div>
					<#-- Stepper -->

				</div>
			</section>
			<#-- /Main area -->
		