<#-- @vtlvariable name="plan" type=\"java.lang.Boolean\" -->
<#if plan==true>
select sum(plan_value) from table
<#else>
select sum(fact_value) from table
</#if>