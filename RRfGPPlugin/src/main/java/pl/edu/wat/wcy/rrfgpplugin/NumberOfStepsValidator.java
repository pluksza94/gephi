/*
 * Copyright 2008-2017 Gephi
 * Authors : Paweł Łuksza
 * 
 * This file is part of Gephi.
 *
 * Gephi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Gephi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */

package pl.edu.wat.wcy.rrfgpplugin;

import org.netbeans.validation.api.Problems;
import org.netbeans.validation.api.Validator;

public class NumberOfStepsValidator implements Validator<String> {

    public NumberOfStepsValidator() {
    }

    @Override
    public boolean validate(Problems prblms, String string, String model) {
        boolean result = false;
        try {
            Integer i = Integer.parseInt(model);
            result = (i > 0 && i < 21);
        } catch (Exception e) {
        }
        if (!result) {
            String message = "Liczba kroków powinna zawierać się w przedziale od 1 do 20";
            prblms.add(message);
        }
        return result;
    }

}
