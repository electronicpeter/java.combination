/**
 * PERFECT.PERMUTATION.API.YML
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
import { PerfectPermutationMetaInfo } from './perfectPermutationMetaInfo';
import { PerfectPermutationSquare } from './perfectPermutationSquare';


/**
 * PerfectPermutationResponseContent
 */
export interface PerfectPermutationResponseContent { 
    metainfo?: PerfectPermutationMetaInfo;
    square?: PerfectPermutationSquare;
    cycles?: Array<Array<Array<number>>>;
}

