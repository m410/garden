
/**
 * This is a module that gets implemented by the application class and gets run
 * at build time.  It generates the orm.xml and persistence.xml files needed
 * at run time.
 *
 * <ul>
 *   <li>description                  </li>
 *   <li>table                        </li>
 *   <li>sequence-generator           </li>
 *   <li>table-generator              </li>
 *   <li>named-query                  </li>
 *   <li>named-native-query           </li>
 *   <li>named-stored-procedure-query </li>
 *   <li>entity-listeners             </li>
 *   <li>pre-persist                  </li>
 *   <li>post-persist                 </li>
 *   <li>pre-remove                   </li>
 *   <li>post-remove                  </li>
 *   <li>pre-update                   </li>
 *   <li>post-update                  </li>
 *   <li>post-load                    </li>
 *   <li>attributes:                  </li>
 *     <li>description        </li>
 *     <li>id                 </li>
 *       <li>temporal           </li>
 *       <li>table-generator    </li>
 *     <li>embedded-id        </li>
 *     <li>basic              </li>
 *     <li>version            </li>
 *     <li>many-to-one        </li>
 *       <li>join-column        </li>
 *       <li>foreign-key        </li>
 *       <li>join-tabla         </li>
 *       <li>cascade            </li>
 *     <li>one-to-many        </li>
 *     <li>one-to-one         </li>
 *     <li>many-to-many       </li>
 *     <li>element-collection </li>
 *     <li>embedded           </li>
 *     <li>transient          </li>
 * </ul>
 */
package org.m410.j8.module.ormbuilder;