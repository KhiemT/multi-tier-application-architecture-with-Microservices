package com.mycompany.app.idgenerator.api;

import com.mycompany.app.idgenerator.api.dto.TransactionIdDTO;

public interface IdGeneratorResource {

    /**
     * @api {get} /api/idgenerator?entity={entityTypeCode} Generate an Unique ID for entity type
     * @apiName generateIdentificationCode
     * @apiGroup IdGenerator
     * @apiDescription Retrieves a unique id code for the specific entity type
     * @apiParam {String} entity case sensitive entity type (e.g. organisation | audit | ...)
     * @apiSuccess {String} code a unique id code for the entity type
     * @apiSuccessExample Success-Response:
     *
     * HTTP/1.1 200 OK
     * {
     *   "code":"ZC02000000"
     * }
     *
     *
     * @apiError ENTITY_TYPE_CODE_NOT_FOUND Could not find mapping for entity
     * @apiErrorExample Error-Response:
     * HTTP/1.1 404 Not Found
     * {
     *   "errorCode":"ENTITY_TYPE_CODE_NOT_FOUND",
     *   "errorMessage":"ENTITY TYPE CODE NOT FOUND",
     *   "params":{"entity":"notexistingentitycode"}
     * }
     *
     */
    TransactionIdDTO generateIdentificationCode(String entity);
}
