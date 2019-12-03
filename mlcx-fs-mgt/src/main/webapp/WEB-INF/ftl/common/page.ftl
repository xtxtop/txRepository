<#macro pagination value uri=uri refreshId='list-page' >
<#if value?exists>
å±æ${value.totalCount}è®°å½,${value.totalPages}é¡µ å½åæ¯${value.pageNo}é¡µ
<a href="${uri}?${value.fullUrl}&pageNo=1" class="ajax" refreshId='${refreshId}' >é¦é¡µ</a>
<#if value.isHasPre()>
<a href="${uri}?${value.fullUrl}&pageNo=${value.pageNo-1}" class="ajax" refreshId='${refreshId}' >ä¸ä¸é¡µ</a>
<#else>
<a href="javascript:;">ä¸ä¸é¡µ</a>
</#if>
<#if value.isHasNext()>
<a href="${uri}?${value.fullUrl}&pageNo=${value.pageNo+1}"  class="ajax" refreshId='${refreshId}'  >ä¸ä¸é¡µ</a><#else>
<a href="javascript:;">ä¸ä¸é¡µ</a>
</#if>
<a href="${uri}?${value.fullUrl}&pageNo=${value.totalPages}" class="ajax" refreshId='${refreshId}' >æ«é¡µ</a>
<a href="${uri}?${value.fullUrl}&pageNo=${value.pageNo}" class="ajax refresh" refreshId='${refreshId}' >å·æ°</a>
<#else>
Controller ä¸­æªå®ä¹pageå¯¹è±¡
</#if>
</#macro>

<#macro linkpage value=page uri=uri param='' class="colorbox" refreshId='list-page' >
<#if value?exists>
 <a href="${uri}?${value.fullUrl}&pageNo=${value.pageNo}&param=${param}"  class="${class}" refreshId='${refreshId}'  ><#nested></a>
</#if>
</#macro>

<#macro pagesort field value=page  sort='desc' uri=uri refreshId='list-page' >
<#if value?exists>
 <a href="${uri}?${value.url}&pageNo=${value.pageNo}&o.field=${field}&o.sort=${value.o.togger}"  class="ajax" refreshId='${refreshId}'  ><#nested>
  <#if field==value.o.field!><img src="${base }/res/app/images/${value.o.sort}.gif" /></#if>
 </a>
</#if>
</#macro>
